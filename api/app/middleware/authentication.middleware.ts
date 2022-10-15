import {NextFunction, Response} from "express";

const db = require('../../config/db');
const errors = require('../services/errors.service');

async function findUserIdByToken(token: string | undefined) {
    const findSQL = 'SELECT id FROM `User` WHERE `auth_token` = ?';

    if (!token) {
        // No token provided, hence can't fetch matching user
        return null;
    }

    try {
        const pool = await db.getPool();
        const rows = await pool.query(findSQL, token);
        console.log("FindUserIDByToken Result: " + JSON.stringify(rows));
        if (rows.length < 1) {
            // No matching user for that token
            return null;
        } else {
            // Return matching user
            return rows[0].id;
        }
    } catch (err) {
        console.error(err)
        errors.logSqlError(err);
        throw err;
    }
}

exports.loginRequired = async function (req: { header: (arg0: string) => any; authenticatedUserId: any; }, res: Response, next: NextFunction) {
    const token = req.header('X-Authorization');

    try {
        const result = await findUserIdByToken(token);
        if (result === null) {
            res.statusMessage = 'Unauthorized';
            res.status(401).send();
        } else {
            req.authenticatedUserId = result;
            next();
        }
    } catch (err) {
        res.statusMessage = 'Internal Server Error';
        res.status(500).send();
    }
};
