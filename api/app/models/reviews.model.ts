import {CreateReview} from "../../@types/app/models/reviews.model";
const db = require('../../config/db');
const errorService = require('../services/errors.service');

exports.create = async function (review: CreateReview, userId: number, beerId: number) {
    const sql = "INSERT INTO `Review` (`rating`, `title`, `description`, `beer`, `user`) VALUES (?, ?, ?, ?, ?)";
    const data = [review.rating, review.title || null, review.description || null, beerId, userId];

    try {
        const pool = await db.getPool();
        const result = await pool.query(sql, data);
        return result.insertId;
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}

exports.getByBeer = async function (beerId: number, size: number, page: number) {
    const sql = "SELECT * FROM `Review` WHERE beer = ? LIMIT ? OFFSET ?";
    const data = [beerId, size, size * page];

    try {
        const pool = await db.getPool();
        return await pool.query(sql, data);
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}
