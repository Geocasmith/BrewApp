import {BeerCreate} from "../../@types/app/models/beer.model";

const db = require('../../config/db');
const errorService = require('../services/errors.service');


exports.create = async function (beer: BeerCreate) {
    const sql = "INSERT INTO `Beer` (`name`, `barcode`, `type`, `photo_path`) VALUES (?, ?, ?, ?)";
    const data = [beer.name, beer.barcode, beer.type, beer.photoUrl || null];

    try {
        const pool = await db.getPool();
        const result = await pool.query(sql, data);
        return result.insertId;
    } catch (e) {
        errorService.logSqlError(e);
    }
}

exports.get = async function () {
    const sql = 'SELECT *, (SELECT AVG(rating) from Review where beer = Beer.id) as rating FROM `Beer`';

    try {
        const pool = await db.getPool();
        return await pool.query(sql);
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}

exports.getByBarcode = async function (barcode: number) {
    const sql = 'SELECT *, (SELECT AVG(rating) from Review where beer = Beer.id) as rating FROM `Beer` WHERE barcode = ?';
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
    const sql = 'SELECT *, (SELECT AVG(rating) from Review where beer = Beer.id) as rating FROM `Beer` ORDER BY RAND() LIMIT 1';
    try {
        const pool = await db.getPool();
        const result = await pool.query(sql);
        return result[0];
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}

exports.getByType = async function (type: string) {
    const sql = 'SELECT *, (SELECT AVG(rating) from Review where beer = Beer.id) as rating FROM `Beer` WHERE type = ?';
    try {
        const pool = await db.getPool();
        return await pool.query(sql, [type]);
    } catch (e) {
        errorService.logSqlError(e);
        console.error(e);
        throw e;
    }
}
