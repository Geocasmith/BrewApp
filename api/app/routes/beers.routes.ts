const beerController = require('../controllers/beers.controller');
// @ts-ignore
const authentication = require('../middleware/authentication.middleware');

module.exports = function (app: any) {
    const baseUrl = app.rootUrl + '/beer';
    app.route(baseUrl)
        .post(authentication.loginRequired, beerController.create);
}