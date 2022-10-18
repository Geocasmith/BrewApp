import {Request, Response} from "express";

const reviewModel = require('../models/reviews.model');
const errorService = require('../services/errors.service');

exports.create = async function (req: {
    params: any;
    body: any; authenticatedUserId: any; }, res: Response) {
    try {
        const reviewId = await reviewModel.create(req.body, req.authenticatedUserId, req.params.beerId);
        res.statusMessage = "Created";
        res.status(201).json({reviewId});
    }catch (e: any) {
        if (e.sqlMessage && e.sqlMessage.includes('Duplicate entry')) {
            res.statusMessage = "Bad Request: Cannot review a beer multiple times";
            res.status(400).send();
        } else {
            if (!e.hasBeenLogged) {
                console.error(e);
            }
            res.status(500).send();
        }
    }
}

exports.getByBeerId = async function (req:Request, res:Response) {
    const beerId = parseInt(req.params.beerId);

    try {
        const reviews = await reviewModel.getByBeer(beerId);
        res.statusMessage = "OK"
        res.status(200).json(reviews);
    } catch (e) {
        res.status(500).send();
    }
}

exports.getMyReviews = async function (req: { authenticatedUserId: any; }, res:Response) {
    const userId = req.authenticatedUserId;
    try {
        const reviews = await reviewModel.getMyReviews(userId);
        res.statusMessage = "OK"
        res.status(200).json(reviews);
    } catch (e) {
        res.status(500).send();
    }
}

exports.get = async function (req:Request, res:Response) {
    try {
        const reviews = await reviewModel.get();
        res.statusMessage = "OK"
        res.status(200).json(reviews);
    } catch (e) {
        res.status(500).send();
    }
}
