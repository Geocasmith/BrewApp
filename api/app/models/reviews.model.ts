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

exports.getByBeer = async function (beerId: number) {
    const sql = "SELECT R.id as id, rating, title, U.name as reviewerName, U.id as user FROM Review R JOIN User U on R.user = U.id WHERE beer = ?";
    const data = [beerId];

    try {
        const pool = await db.getPool();
        return await pool.query(sql, data);
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}

exports.getMyReviews = async function (userId: number) {
    const sql = "SELECT R.id as id, rating, title, description, B.id as beer, B.name as name, B.photo_path as photo_path, type, barcode, (SELECT AVG(rating) from Review where beer = B.id) as averageRating, U.name as reviewerName FROM Review R JOIN Beer B on B.id = R.beer JOIN User U on R.user = U.id WHERE user = ?"
    try {
        const pool = await db.getPool();
        return await pool.query(sql, [userId]);
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}

exports.get = async function () {
    const sql = "SELECT R.id as id, rating, title, description, B.id as beer, B.name as name, B.photo_path as photo_path, type, barcode, (SELECT AVG(rating) from Review where beer = B.id) as averageRating, U.name as reviewerName FROM Review R JOIN Beer B on B.id = R.beer JOIN User U on R.user = U.id"
    try {
        const pool = await db.getPool();
        return await pool.query(sql);
    } catch (e) {
        errorService.logSqlError(e);
        throw e;
    }
}
