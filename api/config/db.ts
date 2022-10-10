import mysql from "promise-mysql";

let pool: import("bluebird") <mysql.Pool> | null = null;

exports.createPool = async (config: mysql.PoolConfig | null) =>{
    pool = mysql.createPool({
        user: process.env.DB_USER, // e.g. 'my-db-user'
        password: process.env.DB_PASS, // e.g. 'my-db-password'
        database: process.env.DB_NAME, // e.g. 'my-database'
        socketPath: process.env.INSTANCE_UNIX_SOCKET, // e.g. '/cloudsql/project:region:instance'
        ...config
    });
};


exports.getPool = function () {
    return pool;
};
