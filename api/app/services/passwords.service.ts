const bcrypt = require('bcrypt');
const SALT_ROUNDS = 10;

exports.hash = async function (password: string) {
    return bcrypt.hash(password, SALT_ROUNDS);
};

exports.compare = async function (data: string, hash: string) {
    return bcrypt.compare(data, hash);
};
