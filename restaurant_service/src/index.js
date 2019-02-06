const restaurants = [
    {id: '1', name: 'Euphoria'},
    {id: '2', name: 'Indigo'},
    {id: '3', name: 'Hugo City'}

];

const restaurant_type = [
    {restaurantId: '1', typeId: '1'},
    {restaurantId: '1', typeId: '2'},
    {restaurantId: '1', typeId: '4'},
    {restaurantId: '3', typeId: '5'},
    {restaurantId: '2', typeId: '3'},
   
];

const Koa = require('koa');
const app = new Koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({server});
const Router = require('koa-router');
const cors = require('koa-cors');
const bodyparser = require('koa-bodyparser');

const request = require('request');

app.use(bodyparser()); //1
app.use(cors()); //2
app.use(async (ctx, next) => { // logger
    const start = new Date();
    await next();
    const ms = new Date() - start;
    console.log(`${ctx.method} ${ctx.url} ${ctx.response.status} - ${ms}ms`);
});

app.use(async (ctx, next) => { // error handler
    try {
        await next();
    } catch (err) {
        ctx.response.body = {issue: [{error: err.message || 'Unexpected error'}]};
        ctx.response.status = 500; // internal server error
    }
});

const router = new Router();
router.get('/restaurants', async (ctx) => {

    ctx.response.body = restaurants;
    ctx.response.status = 200;

});

async function getAllTypes(restaurantId) {
    return new Promise(function (resolve, reject) {

        let typeIdInRestaurant = [];
        restaurant_type.forEach(function (value) {
            if (value.restaurantId === restaurantId) {
                typeIdInRestaurant.push(value.typeId)
            }
        });

        request('http://127.0.0.1:8080/types', {json: true}, (err, res, body) => {
            if (err) {
                reject()
            } else {
                let typeInRestaurant = [];
                body.forEach(function (type) {
                    typeIdInRestaurant.forEach(function (typeId) {
                        if (typeId === type.id) {
                            typeInRestaurant.push(type)
                        }
                    });
                });
                resolve(typeInRestaurant);
            }
        });
    })
}


router.get('/types/:id', async (ctx) => {
    let restaurantId = ctx.params.id;
    var response = await getAllTypes(restaurantId);
    ctx.response.body = response;
    ctx.response.status = 200;


});

app.use(router.routes());
app.use(router.allowedMethods());
server.listen(8081);