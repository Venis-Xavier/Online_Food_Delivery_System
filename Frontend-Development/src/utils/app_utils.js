import { deleteData, getData, postData, putData } from "./api_utils";
import { ADD, AUTH, CREATE, DELETE, FORM_DATA, JSON, LOGIN, MENU, ORDER, PROFILE, REGISTER, RESTAURANT, UPDATE, UPLOAD, VIEW } from "./endpoints";
import { jwtDecode } from "jwt-decode";
 import { validateAddress, validateBoolean, validateDescription, validateEmail, validateId, validateName, validatePassword, validatePhoneNumber, validatePrice, validateQuantity } from "./validation";

const authenticateUser = async (payload) => {
    try {
        const response = await postData(AUTH, LOGIN, payload, false);
        if(response?.success) {
            localStorage.setItem("token", response.data);
        }
        return response?.success;
    } catch (error) {
        console.error("Authentication failed:", error);
    }
};

const registerUser = async (payload) => {
    try {
        if(!validateEmail(payload.email)){
            alert("Invalid Email");
            return;
        }
        if(!validateName(payload.name)){
            // alert("Invalid Name");
            return;
        }
        if(!validatePhoneNumber(payload.phone)){
            alert("Invalid Phone Number");
            return;
        }
        if(!validateAddress(payload.address)){
            alert("Invalid Address");
            return;
        }
        if(!validatePassword(payload.password)){
            alert("Password Must be 6 Characters Long.");
            return;
        }
        const response = await postData(AUTH, REGISTER, payload, false);
        return response?.success;
    } catch (error) {
        console.error("Registration failed:", error);
    }
};

const placeOrder = async (payload) => {
    try {
        console.log("Payload", payload);
        if(!validateId(payload.orderId)){
            alert("Cart is Empty");
            return;
        }
        const response = await putData(ORDER, UPDATE, payload);
        return response?.success;
    } catch (error) {
        console.error("Failed to Place Order:", error);
    }
};

const getUserProfile = async () => {
    try {
        const response = await getData(AUTH, PROFILE);
        return response?.data;
    } catch (error) {
        console.error("Couldn't Fetch User Profile:", error);
    }
}

const addItemToCart = async (payload) => {
    try {
        if(!validateId(payload.itemId)){
            alert("Invalid Item Id. Try Refreshing the Page.");
            return;
        }
        if(!validateId(payload.restaurantId)){
            alert("Invalid Restaurant Id. Try Refreshing the Page.");
            return;
        }
        if(!validateQuantity(payload.quantity)){
            alert("Quantity must be greater than 0");
            return;
        }
        const response = await postData(ORDER, ADD, payload);
        return response?.success;
    } catch (error) {
        console.error("Couldn't Add Item to Cart:", error);
    }
}

const createMenuItem = async (payload) => {
    try {
        if(!validateName(payload.get("itemName"))){
            alert("Item Name must be atleast 10 chars long.");
            return;
        }
        if(!validateDescription(payload.get("itemDesc"))){
            alert("Description must be atleast 20 chars long.");
            return;
        }
        if(!validatePrice(payload.get("price"))){
            alert("Price must be greater than 0.");
            return;
        }
        if(!(validateBoolean(payload.get("isAvailable")))){
            alert("Invalid Value. Try Refreshing the Page.");
            return;
        }
        if(!(validateBoolean(payload.get("isVeg")))){
            alert("Invalid Value. Try Refreshing the Page.");
            return;
        }
        const response = await postData(MENU, CREATE, payload, true, FORM_DATA);
        return response?.data.success;
    } catch (error) {
        console.error("Couldn't Create Menu Item:", error);
    }
}

const updateMenuItem = async (payload) => {
    try {
        if(!validateId(payload.get("itemId"))){
            alert("Invalid Item Id. Try Refreshing the Page.");
            return;
        }
        if(!validateName(payload.get("itemName"))){
            alert("Item Name must be between 10 to 50 chars long.");
            return;
        }
        if(!validateDescription(payload.get("itemDesc"))){
            alert("Description must be between 20 to 250 chars long.");
            return;
        }
        if(!validatePrice(payload.get("price"))){
            alert("Price must be greater than 0.");
            return;
        }
        if(!(validateBoolean(payload.get("isAvailable")))){
            alert("Invalid Value. Try Refreshing the Page.");
            return;
        }
        if(!(validateBoolean(payload.get("isVeg")))){
            alert("Invalid Value. Try Refreshing the Page.");
            return;
        }

        if(!validateId(payload.get("itemImage"))){
            alert("Upload an Item Image.");
            return;
        }

        const response = await putData(MENU, UPDATE, payload, FORM_DATA);
        return response?.data.success;
    } catch (error) {
        console.error("Couldn't Update Menu Item:", error);
    }
}

const deleteMenuItem = async (payload) => {
    if(!validateId(payload.itemId)){
        alert("Invalid Item Id. Try Refreshing the Page.");
        return;
    }
    try {
        await deleteData(MENU, DELETE, payload);
    } catch (error) {
        console.error("Couldn't Delete Menu Item:", error);
    }
}

const removeCartItem = async (payload) => {
    try {
        if(!validateId(payload)){
            alert("Invalid Cart ID. Try Refreshing the page.")
            return;
        }
        await deleteData(ORDER, DELETE, payload);
    } catch (error) {
        console.error("Couldn't Delete Menu Item:", error);
    }
}

const getUserRole = () => {
    const token = localStorage.getItem("token");
    if (token) {
        const decodedToken = jwtDecode(token);
        return decodedToken?.role;
    }
    return null;
};

const isAuthenticated = () => {
    const token = localStorage.getItem("token");
    if (token) {
        const decodedToken = jwtDecode(token);
        const currentTime = Date.now() / 1000;
        return decodedToken.exp > currentTime;
    }
    return false;
};

const fetchOrderSummary = async (payload) => {
    try {
        const response = await postData(ORDER, VIEW, payload, true, JSON);
        return response;
    } catch (error) {
        console.error("Couldn't Fetch Order Summary:", error);
    }
}

const updateProfile = async (payload) => {
    try {
        if(payload.password !== payload.confirmPwd){
            alert("Passwords do not match");
            return;
        }
        if(!validateName(payload.name)){
            alert("Invalid Name");
            return;
        }
        if(!validatePhoneNumber(payload.phone)){
            alert("Invalid Phone Number");
            return;
        }
        if(!validateAddress(payload.address)){
            alert("Invalid Address");
            return;
        }
        const response = await putData(AUTH, UPDATE, payload);
        return response?.success;
    } catch (error) {
        console.error("Couldn't Update User Profile:", error);
    }
}

const uploadRestaurantImage = async (payload) => {
    try {
        if(!payload.get("image")){
            alert("Upload a Restaurant Image.");
            return;
        }
        const response = await putData(RESTAURANT, UPLOAD, payload, FORM_DATA);
        return response?.success;
    } catch (error) {
        console.error("Couldn't Update Restaurant Image:", error);
    }
}

const calculateDeliveryCharge = (billAmount) => {
    return billAmount * 0.1;
}

const calculateTaxes = (billAmount) => {
    return billAmount * 0.18;
}

const calculateSubTotal = (billAmount) => {
    return billAmount - calculateTaxes(billAmount) - calculateDeliveryCharge(billAmount);
}

const formatDate = (isoString) => {
    const dateObj = new Date(isoString);
    const date = dateObj.toISOString().split('T')[0];
    let hours = dateObj.getHours();
    const minutes = dateObj.getMinutes();
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12 || 12; 
    const time = `${hours}:${minutes < 10 ? '0' : ''}${minutes}${ampm}`;
    return `${date} ${time}`;
  };


export {
    authenticateUser,
    registerUser,
    placeOrder,
    addItemToCart,
    calculateSubTotal,
    calculateTaxes,
    calculateDeliveryCharge,
    createMenuItem,
    updateMenuItem,
    deleteMenuItem,
    fetchOrderSummary,
    getUserRole,
    isAuthenticated,
    getUserProfile,
    removeCartItem,
    updateProfile,
    uploadRestaurantImage,
    formatDate
};