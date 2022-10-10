import dotenv from 'dotenv';
const db = require('./config/db');
const express = require('./config/express');

dotenv.config();
const app = express();
const port = process.env.PORT || 8080;


// Test connection to MySQL on start-up
async function testDbConnection() {
    try {
        await db.createPool(null);
        await db.getPool().then((pool: { getConnection: () => any; }) => {
            console.log("Connection to MySQL Established")
            return pool.getConnection();
        });
    } catch (err: any) {
        console.error(`Unable to connect to MySQL`);
        console.error(err.message);
    }
}

testDbConnection()
    .then(function () {
        app.listen(port, function () {
            console.log(`Listening on port: ${port}`);
        });
    });
