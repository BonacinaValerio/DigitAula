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
            <TextInput source="id" validate={[required()]} />
            <TextInput source="roomId" validate={[required()]}/>
            <TextInput source="title" validate={[required()]}/>
            <TextInput source="description" validate={[required()]}/>

        </SimpleForm>
        </Create>
    );
};

export default PostCreate;
