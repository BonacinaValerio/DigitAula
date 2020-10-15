import RichTextInput from 'ra-input-rich-text';
import * as React from 'react';
import {
    Edit,
    SimpleForm ,
    TextInput,
    required,
} from 'react-admin'; // eslint-disable-line import/no-unresolved


const PostEdit = ({ permissions, ...props }) => (
    <Edit {...props}>
        
        <SimpleForm redirect="list">

                <TextInput disabled source="id" />
        <TextInput source="roomId" validate={[required()]}/>
        <TextInput source="title" validate={[required()]}/>
        <TextInput source="description" validate={[required()]}/>

        </SimpleForm>
    </Edit>
);

export default PostEdit;
