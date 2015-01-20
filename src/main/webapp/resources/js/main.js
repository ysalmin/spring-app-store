/**
 * Developer: salm
*/
$(document).ready(function() {
    $('#appUploadForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                message: 'The application name is not valid',
                validators: {
                    notEmpty: {
                        message: 'The username is required and cannot be empty'
                    },
                    stringLength: {
                        min: 3,
                        max: 15,
                        message: 'The app name must be more than 3 and less than 15 characters long'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: 'The username can only consist of alphabetical, number and underscore'
                    }
                }
            },
            description: {
                validators: {
                    notEmpty: {
                        message: 'Description cant be empty'
                    },
                    stringLength: {
                        min: 10,
                        max: 500,
                        message: 'The app description must be more than 10 and less than 500 characters long'
                    }
                }
            },
            file: {
                validators: {
                    notEmpty: {
                        message: 'ZIP archive should be attached'
                    }
                }
            }
        }
    });

});