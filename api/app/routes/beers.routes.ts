const beerController = require('../controllers/beers.controller');
// @ts-ignore
const authentication = require('../middleware/authentication.middleware');

module.exports = function (app: any) {
    const baseUrl = app.rootUrl + '/beer';
    app.route(baseUrl)
        .post(authentication.loginRequired, beerController.create)
        .get(authentication.loginRequired, beerController.get);

    app.route(baseUrl + '/random')
        .get(authentication.loginRequired, beerController.getRandom)

    app.route(baseUrl + '/:code')
        .get(authentication.loginRequired, beerController.getByBarcode)
}
