import {Request, Response} from "express";

const beerModel = require('../models/beer.model')
const errorService = require('../services/errors.service');


exports.create = async function (req: Request, res: Response) {
    try {
        const beerId = await beerModel.create(req.body);
        res.statusMessage = "Created";
        res.status(201).json({beerId});
    }catch (e: any) {
        if (e.sqlMessage && e.sqlMessage.includes('Duplicate entry')) {
            res.statusMessage = "Bad Request: beer name already in database";
            res.status(400).send();
        } else {
            if (!e.hasBeenLogged) {
                console.error(e);
            }
            res.status(500).send();
        }
    }
};