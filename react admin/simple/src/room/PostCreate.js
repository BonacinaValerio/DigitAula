import * as React from 'react';
import {
    Create,
    SimpleForm,
    required,
    TextInput
} from 'react-admin'; // eslint-disable-line import/no-unresolved


const PostCreate = ({ permissions, ...props }) => {

    return (
        <Create {...props}>
            <SimpleForm redirect="list">

                <TextInput source="id" validate={[required()]}/>
                <TextInput source="name" validate={[required()]}/>
                <TextInput source="idClass" validate={[required()]}/>
                <TextInput source="live" />
                <TextInput source="drive"/>
            </SimpleForm>
        </Create>
    );
};

export default PostCreate;
