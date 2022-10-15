import {BeerCreate} from "../../@types/app/models/beer.model";

const db = require('../../config/db');
const errorService = require('../services/errors.service');


exports.create = async function (beer: BeerCreate) {
    const sql = "INSERT INTO `Beer` (`name`, `barcode`, `type`) VALUES (?, ?, ?)";
    const data = [beer.name, beer.barcode, beer.type];

    try {
        const pool = await db.getPool();
        const result = await pool.query(sql, data);
        return result.insertId;
    } catch (e) {
        errorService.logSqlError(e);
    }
}

exports.get = async function (amount: number, page: number) {
    const sql = 'SELECT * FROM `Beer` LIMIT ? OFFSET ?';
    const data = [amount, amount * page]

    try {
        const pool = await db.getPool();
        return await pool.query(sql, data);
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}

exports.getByBarcode = async function (barcode: number) {
    const sql = 'SELECT * FROM `Beer` WHERE barcode = ?';
    try {
        const pool = await db.getPool();
        const result = await pool.query(sql, [barcode]);
        return result[0] || null;
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}

exports.getRandom = async function () {
    const sql = 'SELECT * FROM `Beer` ORDER BY RAND() LIMIT 1';
    try {
        const pool = await db.getPool();
        const result = await pool.query(sql);
        return result[0];
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}
