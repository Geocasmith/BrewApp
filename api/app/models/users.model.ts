import {UserCreate} from "../../@types/app/models/users.model";

const db = require('../../config/db');
const passwordService = require('../services/passwords.service');
const errorService = require('../services/errors.service');

exports.create = async function (user: UserCreate) {
    const sql = "INSERT INTO `User` (`name`, `username`, `password`) VALUES (?, ?, ?)";
    const data = [user.name, user.username, await passwordService.hash(user.password)];

    try {
        const pool = await db.getPool();
        console.log('here');
        const result = await pool.query(sql, data);
        console.log('here2');
        console.log(JSON.stringify(result));
        return result[0].insertId;
    } catch (e) {
        errorService.logSqlError(e);
        console.error(e)
        throw e;
    }
};
