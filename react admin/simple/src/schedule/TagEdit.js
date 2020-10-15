
/* eslint react/jsx-key: off */
import * as React from 'react';
import { Edit, SimpleForm, TextField, TextInput, required, Labeled, SelectInput  } from 'react-admin';
import { makeStyles } from '@material-ui/core/styles';

const TagEdit = props => (
    <Edit {...props}>
        <SimpleForm redirect="list">
            <TextField source="id" />
            <TextInput source="parent_id" label="ID classe/ID giornata" />
            <TextInput source="subject_id" label="ID stanza della materia"/>
            <TextInput source="name" validate={[required()]}/>
            <TextInput source="prof"  />
            <Labeled label="start" />
            <div style={{ 
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'flex-start',
                width: '50%' }}>
                <TextInput source="startAtHour" style={{ 
                    marginRight:"1em" }} />
                <TextInput source="startAtMinutes"  />
            </div>
            <Labeled label="end" />
            <div style={{ 
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'flex-start',
                width: '50%' }}>
            <TextInput source="endAtHour"  style={{ 
                    marginRight:"1em" }}  />
            <TextInput source="endAtMinutes"  />
            </div>
            
        </SimpleForm>
    </Edit>
);

export default TagEdit;
