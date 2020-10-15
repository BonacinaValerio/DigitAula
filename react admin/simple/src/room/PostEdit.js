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
                <TextInput source="name" validate={[required()]}/>
                <TextInput source="idClass" validate={[required()]}/>
                <TextInput source="live" />
                <TextInput source="drive" />
        </SimpleForm>
    </Edit>
);

export default PostEdit;
