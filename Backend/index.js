require("dotenv").config();
const express = require('express');
const bodyParser = require('body-parser');
const accountRepo = require('./repositories/repository.account');

const port = process.env.port;
const app = express();


//Middleware
app.use(bodyParser.json());
app.use(express.urlencoded({ extended: true }));


//Endpoint
app.get('/status', (req, res) => {
    res.status(200).json({ status: 'Server is running' });
});
app.post('/register', accountRepo.registerAccount);
app.post('/login', accountRepo.loginAccount);


app.listen(port, () => {
    console.log("🚀 Server is running and listening on port", port);
});