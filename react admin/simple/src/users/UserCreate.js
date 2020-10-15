/* eslint react/jsx-key: off */
import * as React from 'react';
import {
    Create,
    FormTab,
    SaveButton,
    SelectInput,
    TabbedForm,
    TextInput,
    Toolbar,
    required,
} from 'react-admin';


const UserEditToolbar = ({
    permissions,
    hasList,
    hasEdit,
    hasShow,
    hasCreate,
    ...props
}) => (
    <Toolbar {...props}>
        <SaveButton
            label="user.action.save_and_show"
            redirect="show"
            submitOnEnter={true}
        />
        {permissions === 'admin' && (
            <SaveButton
                label="user.action.save_and_add"
                redirect={false}
                submitOnEnter={false}
                variant="text"
            />
        )}
    </Toolbar>
);

const UserCreate = ({ permissions, ...props }) => (
    <Create {...props}>
        <TabbedForm
            defaultValue={{ role: 'user' }}
            toolbar={<UserEditToolbar />}
        >
            <FormTab label="user.form.summary" path="">
                <TextInput source="id" />
                <TextInput
                    source="name"
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
    </Create>
);

export default UserCreate;
