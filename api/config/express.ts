import express, {Request, Response} from "express";

const bodyParser = require('body-parser');
const jsonParser = bodyParser.json();

module.exports = function () {
    const app: any = express();
    app.use(jsonParser);
    app.use((req: any, res: any, next: any) => {
        console.log(`##### ${req.method} ${req.path} #####`);
        next();
    });
    app.rootUrl = '/api';
    app.get('/', (req: Request, res: Response) => {
        res.send('Welcome to our amazingly awesome API for our SENG440 Assignment 2 App');
    });
    require('../app/routes/users.routes')(app);
    require('../app/routes/beers.routes')(app);
    return app;
}
