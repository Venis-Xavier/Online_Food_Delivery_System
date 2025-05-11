const LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
const NUMBERS = "0123456789"
const EMAIL_SPL_CHARS = "@._-"
// const MIN_PASSWORD_LENGTH = 6;
// const UPPER_CASE_PATTERN = /[A-Z]/;
// const LOWER_CASE_PATTERN = /[a-z]/;
// const NUMBER_PATTERN = /[0-9]/;
// const SYMBOL_PATTERN = /[!@#$%^&*(),.?":{}|<>]/;


function hasUnallowedCharacters(str, allowedChars) {
    for (let char of str) {
        if (!allowedChars.includes(char)) {
            return true;
        }
    }
    return false;
}

function checkCharacterCount(str, min, max) {
    const length = str.length;
    return length >= min && length <= max;
}

function validateEmail(email) {
    const allowedChars = LETTERS + NUMBERS + EMAIL_SPL_CHARS;
    const minLength = 5;
    const maxLength = 254;

    if(email === null || email === ""){
        return false;
    }

    if (hasUnallowedCharacters(email, allowedChars.split(''))) {
        return false;
    }

    if (!checkCharacterCount(email, minLength, maxLength)) {
        return false;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(email);
}

function validatePhoneNumber(phoneNumber) {
    const phoneRegex = /^[0-9]{10}$/; 

    if (!phoneNumber) {
        return false;
    }

    return phoneRegex.test(phoneNumber);
}


function validateName(name) {
    const allowedChars = (LETTERS + ' ').split('');
    const minLength = 3;
    const maxLength = 50;

    if(name === null || name === ""){
        alert("Name is required");
        return false;
    }

    if (hasUnallowedCharacters(name, allowedChars)) {
        alert("Name contains unallowed characters");
        return false;
    }

    if (!checkCharacterCount(name.trim(), minLength, maxLength)) {
        alert("Name should be between " + minLength + " and " + maxLength + " characters");
        return false;
    }

    return true;
}

function validateAddress(address) {
    const allowedChars = NUMBERS + LETTERS + ' ,.-\''.split('');
    const minLength = 7;
    const maxLength = 100;

    if(address === null || address === ""){
        return false;
    }

    if (hasUnallowedCharacters(address, allowedChars)) {
        return false;
    }

    if (!checkCharacterCount(address.trim(), minLength, maxLength)) {
        return false;
    }

    return true;
}

function validateDescription(description) {
    const minLength = 20;
    const maxLength = 250;

    if(description === null || description === ""){
        return false;
    }

    return checkCharacterCount(description, minLength, maxLength);
}

function validateQuantity(quantity) {
    return quantity > 0;
}

function validatePassword(password) {
 
 
    // if (password.length <= MIN_PASSWORD_LENGTH) {
    //     return false;
    // }

    // if (!UPPER_CASE_PATTERN.test(password)) {
    //     return false;
    // }

    // if (!LOWER_CASE_PATTERN.test(password)) {
    //     return false;
    // }

    // if (!NUMBER_PATTERN.test(password)) {
    //     return false;
    // }

    // if (!SYMBOL_PATTERN.test(password)) {
    //     return false;
    // }

    return true;
}

function validateId(id){

    if(id === null || id === ""){
        return false;
    }

    return true;
}

function validateBoolean(boolValue){
    return !(boolValue == null);
}

function validatePrice(price){
    return !(price == null);
}

export {
    validateAddress,
    validateDescription,
    validateEmail,
    validateName,
    validateQuantity,
    validatePhoneNumber,
    validatePassword,
    validateId,
    validateBoolean,
    validatePrice
}