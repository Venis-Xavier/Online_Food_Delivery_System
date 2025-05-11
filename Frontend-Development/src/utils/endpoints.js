//BASE URI
const DOMAIN = "http://localhost";
const PORT = 8080;
const API = "api";

// SERVICES
export const AUTH = "auth";
export const MENU = "menus";
export const ORDER = "order";
export const RESTAURANT = "restaurant";
export const PAYMENT = "payment";
export const DELIVERY = "delivery";

// ENDPOINTS
export const DEFAULT = "";

export const ALL = "all";
export const ADD = "add";
export const VIEW = "view";
export const CREATE = "create";
export const UPDATE = "update";
export const UPLOAD = "upload";
export const DELETE = "delete";
export const STATUS = "status";
export const TOGGLE = "toggle";


export const LOGIN = "login";
export const REGISTER = "register";
export const CUSTOMER = "customer";
export const PROFILE = "profile";

export const JSON = "application/json";
export const FORM_DATA = "multipart/form-data";

/**
 * Function to build request URL.
 * @param {string} service - The service identifier used to define the service path.
 * @param {string} endpoint - The specific endpoint within the service to be called.
 * @param {Object} [pathVariables={}] - An object representing the path variables.
 * @param {Object} [queryParams={}] - An object representing the query parameters.
 * 
 * @returns {string} The fully constructed URL.
 */
function getServiceURL(service, endpoint, pathVariables = {}, queryParams = {}) {
    let path = `${DOMAIN}:${PORT}/${API}/${service}/${endpoint}`;

    for (const [key, value] of Object.entries(pathVariables)) {
        path = path.replace(`:${key}`, value);
    }

    const queryString = new URLSearchParams(queryParams).toString();

    if (queryString) {
        path += `?${queryString}`;
    }

    return path;
}

export default getServiceURL;