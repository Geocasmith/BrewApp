const usersController = require('../controllers/users.controller');
// @ts-ignore
const authentication = require('../middleware/authentication.middleware')

module.exports = function (app: any) {
    const baseUrl = app.rootUrl + '/users';
    app.route(baseUrl)
        .patch(authentication.loginRequired, usersController.edit)

    app.route(baseUrl + '/register').post(usersController.create)

    app.route(baseUrl + '/login')
        .post(usersController.login);

    app.route(baseUrl + '/logout')
        .post(authentication.loginRequired, usersController.logout);

    app.route(baseUrl + '/:id')
        .get(authentication.loginRequired, usersController.findById)
}
