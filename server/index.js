'use strict'

const http = require('http')
const request = require('request');
const path = require('path')
const express = require('express')
const EventEmitter = require('events')
const socketio = require('socket.io')
const debug = require('debug')('node')
const name = 'chaos-broker-app'
debug('booting %s', name)

const port = process.env.PORT || 8888

const app = express()
const server = http.createServer(app)
const io = socketio(server)
const events = new EventEmitter()

io.on('connection', function (socket) {

    io.emit('this', { will: 'be received by everyone' });

    socket.on('private message', function (from, msg) {
        console.log('I received a private message by ', from, ' saying ', msg);
    });

    socket.on('disconnect', function () {
        io.emit('user disconnected');
    });
    
});

io.on('connect', socket => {
    events.on('data', value => {
        socket.emit('data', value)
    })
})

app.use(express.static(path.join(__dirname, 'public')))

server.listen(port, () => console.log(`Listening on port ${port}`))

/**
 * Talk to services brokers
 */
app.patch('/:service/broker/:action/percentage', function (req, res) {

    let serviceUri = getServiceUri(req.params.service, req.params.action)

    if (serviceUri == undefined) return;
    if (req.body == undefined || req.body.percentage == undefined) return;

    request.patch(serviceUri, req.body)
})

const services = {
    "announcement": "8080",
    "accounting": "8081",
    "billing": "8082",
    "matching": "8084",
    "tracking": "8085"
}

const actions = ["delete", "nothing", "salt", "duplicate", "slowdown"]

function getServiceUri(service, action) {

    // TODO: talk to loadBalancer instead of services directly

    if (services[service] == undefined) return undefined
    if (actions.indexOf(action) == -1) return undefined

    return "http://localhost:" + services[service] + "/broker/" + action + "/percentage"
}