const reviewController = require('../controllers/reviews.controller');
// @ts-ignore
const authentication = require('../middleware/authentication.middleware');

module.exports = function (app: any) {
    const baseUrl = app.rootUrl + '/review';
    app.route(baseUrl + "/mine")
        .get(authentication.loginRequired, reviewController.getMyReviews)
    app.route(baseUrl + "/:beerId")
        .post(authentication.loginRequired, reviewController.create)
        .get(authentication.loginRequired, reviewController.getByBeerId);
}
