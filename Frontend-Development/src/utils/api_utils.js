import axios from "axios";
import getServiceURL, { JSON } from "./endpoints";

const TOKEN = localStorage.getItem("token");

/**
 * Function to make GET requests.
 * @param {string} service - The service identifier.
 * @param {string} endpoint - The endpoint within the service.
 * 
 * @returns {Promise<any>} The response data.
 */
export const getData = (service, endpoint) => {
    return requestData("GET", service, endpoint);
};

/**
 * Function to make POST requests.
 * @param {string} service - The service identifier.
 * @param {string} endpoint - The endpoint within the service.
 * @param {object} [payload] - The request body (optional).
 * 
 * @returns {Promise<any>} The response data.
 */
export const postData = (service, endpoint, payload, authenticated, contentType = JSON) => {
    return requestData("POST", service, endpoint, payload, authenticated, contentType);
};

/**
 * Function to make PUT requests.
 * @param {string} service - The service identifier.
 * @param {string} endpoint - The endpoint within the service.
 * @param {object} [payload] - The request body (optional).
 * 
 * @returns {Promise<any>} The response data.
 */
export const putData = (service, endpoint, payload, contentType = JSON) => {
    return requestData("PUT", service, endpoint, payload, true, contentType);
};

/**
 * Function to make DELETE requests.
 * @param {string} service - The service identifier.
 * @param {string} endpoint - The endpoint within the service.
 * @param {object} [payload] - The request body (optional).
 * 
 * @returns {Promise<any>} The response data.
 */
export const deleteData = (service, endpoint, payload) => {
    return requestData("DELETE", service, endpoint, payload);
};

/**
 * Function to make PATCH requests.
 * @param {string} service - The service identifier.
 * @param {string} endpoint - The endpoint within the service.
 * @param {object} [payload] - The request body (optional).
 * 
 * @returns {Promise<any>} The response data.
 */
export const patchData = (service, endpoint, payload) => {
    return requestData("PATCH", service, endpoint, payload);
};



/**
 * A reusable function to make API requests.
 * @param {string} method - The HTTP method (GET, POST, PUT, DELETE, PATCH).
 * @param {string} service - The service identifier.
 * @param {string} endpoint - The endpoint within the service.
 * @param {object} [payload] - The request body (optional).
 * @returns {Promise<any>} The response data.
 */
async function requestData(method, service, endpoint, payload = null, authenticated = true, contentType = JSON) {
    const serviceURL = getServiceURL(service, endpoint);
    let result = null;

    const headers = {
        "Content-Type": contentType,
        ...(authenticated && { Authorization: `Bearer ${TOKEN}` }),
    };

    try {
        const response = await axios({
            method,
            url: serviceURL,
            data: payload,
            headers
        });

        result = response?.data;
    } catch (error) {
        console.error(`ERROR [${method} ${serviceURL}]:`, error);
    }

    return result;
}
