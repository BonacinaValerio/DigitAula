/* eslint react/jsx-key: off */
import * as React from 'react';
import PropTypes from 'prop-types';
import {
    DeleteWithConfirmButton,
    Edit,
    FormTab,
    required,
    SaveButton,
    SelectInput,
    TabbedForm,
    TextInput,
    Toolbar
} from 'react-admin';
import { makeStyles } from '@material-ui/core/styles';


const useToolbarStyles = makeStyles({
    toolbar: {
        display: 'flex',
        justifyContent: 'space-between',
    },
});

/**
 * Custom Toolbar for the Edit form
 *
 * Save with undo, but delete with confirm
 */
const UserEditToolbar = props => {
    const classes = useToolbarStyles();
    return (
        <Toolbar {...props} classes={classes}>
            <SaveButton />
            <DeleteWithConfirmButton />
        </Toolbar>
    );
};

const UserEdit = ({ permissions, ...props }) => (
    <Edit
        {...props}
    >
        <TabbedForm
            defaultValue={{ role: 'user' }}
            toolbar={<UserEditToolbar />}
        >
            <FormTab label="user.form.summary" path="">
                <TextInput disabled source="id" />
                <TextInput
                    source="name"
                    defaultValue="slim shady"
                    validate={required()}
                />
                <TextInput source="email" validate={required()}/>
            </FormTab>
                <FormTab label="user.form.security" path="security">
                    <SelectInput
                        source="role"
                        validate={required()}
                        choices={[
                            { id: 'prof', name: 'Professore' },
                            { id: 'student', name: 'Studente' },
                            { id: 'admin', name: 'Admin' },
                        ]}
                        defaultValue={'student'}
                    />
                </FormTab>
        </TabbedForm>
    </Edit>
);

UserEdit.propTypes = {
    id: PropTypes.any.isRequired,
    location: PropTypes.object.isRequired,
    match: PropTypes.object.isRequired,
    permissions: PropTypes.string,
};

export default UserEdit;
