const usersController = require('../controllers/users.controller');

module.exports = function (app: any) {
const baseUrl = app.rootUrl + '/users';
app.route(baseUrl + '/register').post(usersController.create)
}
