import {Request, Response} from "express";

const usersModel = require('../models/users.model')

exports.create = async function (req: Request, res: Response) {
    // TODO: Validate body
    try {
        const userId = await usersModel.create(req.body);
        res.statusMessage = "Created";
        res.status(201).json({userId});
    } catch (e: any) {
        if (e.sqlMessage && e.sqlMessage.includes('Duplicate entry')) {
            res.statusMessage = "Bad Request: username already in use";
            res.status(400).send();
        } else {
            if (!e.hasBeenLogged) {
                console.error(e);
            }
            res.status(500).send();
        }
    }
};
