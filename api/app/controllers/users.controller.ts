import {Request, Response} from "express";

const usersModel = require('../models/users.model')
const errorService = require('../services/errors.service');
const passwordService = require('../services/passwords.service');

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

exports.login = async function (req: Request, res: Response) {
        try {
            const foundUser = await usersModel.findByUsername(req.body.username);
            if (foundUser == null) {
                // Either no user found or password check failed
                res.statusMessage = 'Bad Request: invalid username/password supplied';
                res.status(400).send();
            } else {
                const passwordCorrect = await passwordService.compare(req.body.password, foundUser.password);
                if (passwordCorrect) {
                    const loginResult = await usersModel.login(foundUser.id);
                    res.statusMessage = 'OK';
                    res.status(200).json(loginResult);
                } else {
                    res.statusMessage = 'Bad Request: invalid username/password supplied';
                    res.status(400).send();
                }
            }
        } catch (err) {
            // Something went wrong with either password hashing or logging in
            res.statusMessage = 'Internal Server Error';
            res.status(500).send();
        }
};

exports.logout = async function (req: { authenticatedUserId: any; }, res: Response) {
    const id = req.authenticatedUserId;

    try {
        await usersModel.logout(id);
        res.statusMessage = 'OK';
        res.status(200).send();
    } catch (err) {
        res.statusMessage = 'Internal Server Error';
        res.status(500).send();
    }
};

exports.findById = async function (req: { params: { id: any; }; authenticatedUserId: any; }, res: Response) {
    const id = parseInt(req.params.id);
    const userData = await usersModel.findById(id);
    if (userData == null) {
        res.statusMessage = 'Not Found';
        res.status(404).send();
    } else {
        res.statusMessage = 'OK';
        res.status(200).json(userData);
    }
};

exports.edit = async function (req: { authenticatedUserId: any; body: any; }, res: Response) {
    const id = req.authenticatedUserId;
    try {
        await usersModel.edit(req.body, id)
        res.statusMessage = 'OK';
        res.status(200).json({id});
    } catch (err) {
        res.statusMessage = 'Internal Server Error';
        res.status(500).send();
    }
}
