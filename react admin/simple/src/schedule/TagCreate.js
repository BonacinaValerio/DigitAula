/* eslint react/jsx-key: off */
import * as React from 'react';
import {
    Create,
    SimpleForm,
    TextField,
    TextInput,
    required,Labeled, SelectInput 
} from 'react-admin';

const TagCreate = props => (
    <>
    <Create {...props} >
        <SimpleForm redirect="list">
            <Labeled label="Classe" />
            <TextInput source="id" validate={[required()]}/>
            <TextInput source="name" validate={[required()]} />
        </SimpleForm>
    </Create>
    <Create {...props}>
        <SimpleForm redirect="list">
            <Labeled label="Giornata della classe" />
            <TextInput source="id" validate={[required()]}/>
            <TextInput source="parent_id" label="ID classe" validate={[required()]} />
            <SelectInput source="name" choices={[
                { id: 'lunedì', name: 'lunedì' },
                { id: 'martedì', name: 'martedì' },
                { id: 'mercoledì', name: 'mercoledì' },
                { id: 'giovedì', name: 'giovedì' },
                { id: 'venerdì', name: 'venerdì' },
                { id: 'sabato', name: 'sabato' },
                { id: 'domenica', name: 'domenica' },
            ]} validate={[required()]}/>
            
        </SimpleForm>
    </Create>
    <Create {...props}>
        <SimpleForm redirect="list">
            <Labeled label="Materia" />
            <TextInput source="id" validate={[required()]}/>
            <TextInput source="parent_id" label="ID giornata" validate={[required()]} />
            <TextInput source="subject_id" label="ID stanza della materia" validate={[required()]} />
            
            <TextInput source="name" validate={[required()]} />
            <TextInput source="prof" validate={[required()]} />
            <Labeled label="start" />
            <div style={{ 
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'flex-start',
                width: '50%' }}>
                <TextInput source="startAtHour" style={{ 
                    marginRight:"1em" }} validate={[required()]} />
                <TextInput source="startAtMinutes" validate={[required()]} />
            </div>
            <Labeled label="end" />
            <div style={{ 
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'flex-start',
                width: '50%' }}>
            <TextInput source="endAtHour"  style={{ 
                    marginRight:"1em" }} validate={[required()]} />
            <TextInput source="endAtMinutes" validate={[required()]} />
            </div>
            
            
        </SimpleForm>
    </Create>
    </>
);

export default TagCreate;
