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

exports.get = async function (req:Request, res:Response) {
    try {
        const beers = await beerModel.get();
        res.statusMessage = "OK"
        res.status(200).json(beers);
    } catch (e) {
        res.status(500).send();
    }
}

exports.getByBarcode = async function (req:Request, res:Response) {
    const barcode = req.params.code;
    try {
        const beer = await beerModel.getByBarcode(barcode);
        if (beer == null) {
            res.status(404).send();
        } else {
            res.status(200).json(beer);
        }
    } catch (e) {
        res.status(500).send();
    }
}

exports.getRandom = async function (req:Request, res:Response) {
    try {
        const beer = await beerModel.getRandom();
        res.statusMessage = "OK";
        res.status(200).json(beer);
    } catch (e) {
        res.status(500).send();
    }
}

exports.getByType = async function (req:Request, res:Response) {
    const type = req.params.type;
    try {
        const beers = await beerModel.getByType(type);
        res.statusMessage = "OK"
        res.status(200).json(beers);
    } catch (e) {
        res.status(500).send();
    }
}
