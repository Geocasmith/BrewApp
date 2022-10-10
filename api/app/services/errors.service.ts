import {SqlError} from "../../@types/app/services/errors.services";

exports.logSqlError = function (err: SqlError) {
    console.error(`An error occurred when executing: \n${err.sql} \nERROR: ${err.sqlMessage}`);
    err.hasBeenLogged = true;
};
