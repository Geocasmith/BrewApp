const reviewController = require('../controllers/reviews.controller');
// @ts-ignore
const authentication = require('../middleware/authentication.middleware');

module.exports = function (app: any) {
    const baseUrl = app.rootUrl + '/review/:beerId';
    app.route(baseUrl)
        .post(authentication.loginRequired, reviewController.create)
        .get(authentication.loginRequired, reviewController.getByBeerId);
}
