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