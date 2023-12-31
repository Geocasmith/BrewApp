import {EditUser, UserCreate} from "../../@types/app/models/users.model";

const db = require('../../config/db');
const passwordService = require('../services/passwords.service');
const errorService = require('../services/errors.service');
const randToken = require('rand-token');

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

exports.login = async function (userId: number) {
    const loginSQL = 'UPDATE `User` SET `auth_token` = ? WHERE `id` = ?';
    const token : string = randToken.generate(32);

    try {
        const pool = await db.getPool();
        await pool.query(loginSQL, [token, userId]);
        return {userId, token}
    } catch (err) {
        errorService.logSqlError(err);
        throw err;
    }
};

exports.logout = async function (userId: number) {
    const logoutSQL = 'UPDATE `User` SET `auth_token` = NULL WHERE `id` = ?';

    try {
        const pool = await db.getPool();
        await pool.query(logoutSQL, [userId]);
    } catch (err) {
        errorService.logSqlError(err);
        throw err;
    }
};

exports.findByUsername = async function (username: string) {
    const findSQL = 'SELECT * FROM `User` WHERE `username` = ?';

    try {
        const pool = await db.getPool();
        const rows = await pool.query(findSQL, [username]);
        console.log("findByUsername result: " + JSON.stringify(rows));
        return rows.length < 1 ? null : rows[0];
    } catch (err) {
        console.error(err);
        errorService.logSqlError(err);
        return null;
    }
}

exports.findById = async function (id: number) {
    const viewSQL = 'SELECT * FROM `User` WHERE `id` = ?';

    try {
        const pool = await db.getPool();
        const rows = await pool.query(viewSQL, id);
        if (rows.length < 1) {
            return null;
        } else {
            const foundUser = rows[0];
            delete foundUser.password;
            return foundUser;
        }
    } catch (err) {
        errorService.logSqlError(err);
        return null;
    }
};

exports.edit = async function (user: EditUser, userId: number) {
    const sql = "UPDATE User SET name = ?, photo_path = ?, favourites = ?, bio = ? WHERE id = ?";
    const data = [user.name, user.photoUrl, user.favourites, user.bio, userId];
    try {
        const pool = await db.getPool();
        await pool.query(sql, data);
        return userId;
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}
