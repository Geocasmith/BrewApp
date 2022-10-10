import {UserCreate} from "../../@types/app/models/users.model";

const db = require('../../config/db');
const passwordService = require('../services/passwords.service');
const errorService = require('../services/errors.service');

exports.create = async function (user: UserCreate) {
    const sql = "INSERT INTO `User` (`name`, `username`, `password`) VALUES (?, ?, ?)";
    const data = [user.name, user.username, await passwordService.hash(user.password)];

    try {
        const pool = await db.getPool();
        const result = await pool.query(sql, data);
        return result.insertId;
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
};
