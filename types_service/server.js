'use strict';

const express = require('express');

// Constants
const PORT = 8080;
const HOST = '0.0.0.0';


const types = [
    {id: '1', title: 'bar'},
    {id: '2', title: 'coffee'},
    {id: '3', title: 'grill'},
    {id: '4', title: 'night club'},
    {id: '5', title: 'fast food'},
];

// App
const app = express();

app.get('/types', (req, res) => {
    res.send(types);
});

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);