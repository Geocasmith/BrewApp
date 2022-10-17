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

exports.getMyReviews = async function (userId: number) {
    const sql = "SELECT R.id as id, rating, title, description, B.id as beer, name, photo_path, type, barcode, (SELECT AVG(rating) from Review where beer = B.id) as averageRating FROM Review R JOIN Beer B on B.id = R.beer WHERE user = ?";
    try {
        const pool = await db.getPool();
        return await pool.query(sql, [userId]);
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}

exports.get = async function () {
    const sql = 'SELECT R.id as id, rating, title, description, B.id as beer, name, photo_path, type, barcode, (SELECT AVG(rating) from Review where beer = B.id) as averageRating FROM Review R JOIN Beer B on B.id = R.beer';

    try {
        const pool = await db.getPool();
        return await pool.query(sql);
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}
