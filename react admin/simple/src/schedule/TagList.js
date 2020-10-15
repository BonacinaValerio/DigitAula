import * as React from 'react';
import { Fragment, useState } from 'react';
import { ListBase, useListContext, EditButton, Title } from 'react-admin';
import {
    Box,
    List,
    ListItem,
    ListItemText,
    ListItemSecondaryAction,
    Collapse,
    Datagrid,
    TextField,
    SimpleList,
    SearchInput,
    Filter,
    TopToolbar,
    CreateButton,
    Card,
} from '@material-ui/core';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';

import Aside from './Aside';

const ListActions = () => (
    <TopToolbar>
    </TopToolbar>
);



const Box2  = () => {
    return (
        <>
            <Box marginTop="1em">
                <Card>
                    <Tree />
                </Card>
            </Box>
        </>
    )
};

const TagList = props => (
    <ListBase perPage={1000} {...props}>
        <Aside />
        <Box2 marginTop="1em"/>
    </ListBase>
);


const Tree = () => {
    const { ids, data, defaultTitle } = useListContext();
    const [openChildren, setOpenChildren] = useState([]);
    const toggleNode = node =>
        setOpenChildren(state => {
            if (state.includes(node.id)) {
                return [
                    ...state.splice(0, state.indexOf(node.id)),
                    ...state.splice(state.indexOf(node.id) + 1, state.length),
                ];
            } else {
                return [...state, node.id];
            }
        });
    const nodes = ids.map(id => data[id]);
    const roots = nodes.filter(node => typeof node.parent_id === 'undefined');
    const getChildNodes = root =>
        nodes.filter(node => node.parent_id === root.id);
    return (
        <List>
            <Title defaultTitle={defaultTitle} />
            {roots.map(root => (
                <SubTree
                    key={root.id}
                    root={root}
                    getChildNodes={getChildNodes}
                    openChildren={openChildren}
                    toggleNode={toggleNode}
                    level={1}
                />
            ))}
        </List>
    );
};

const SubTree = ({
    level,
    root,
    getChildNodes,
    openChildren,
    toggleNode,
    defaultTitle,
}) => {
    const childNodes = getChildNodes(root);
    const hasChildren = childNodes.length > 0;
    const open = openChildren.includes(root.id);
    return (
        <Fragment>
            <ListItem
                button={hasChildren}
                onClick={() => hasChildren && toggleNode(root)}
                style={{ paddingLeft: level * 16 }}
            >
                {hasChildren && open && <ExpandLess />}
                {hasChildren && !open && <ExpandMore />}
                {!hasChildren && <div style={{ width: 24 }}>&nbsp;</div>}
                <ListItemText primary={root.name +" ("+ root.id+")"} />

                <ListItemSecondaryAction>
                                <EditButton record={root} basePath="/schedule" />
                            </ListItemSecondaryAction>
            </ListItem>
            <Collapse in={open} timeout="auto" unmountOnExit>
                <List component="div" disablePadding>
                    {childNodes.map(node => (
                        <SubTree2
                            key={node.id}
                            root={node}
                            getChildNodes={getChildNodes}
                            openChildren={openChildren}
                            toggleNode={toggleNode}
                            level={level + 1}
                        />
                    ))}
                </List>
            </Collapse>
        </Fragment>
    );
};

const SubTree2 = ({
    level,
    root,
    getChildNodes,
    openChildren,
    toggleNode,
    Labeled,
}) => {
    const childNodes = getChildNodes(root);
    const hasChildren = childNodes.length > 0;
    const open = openChildren.includes(root.id);
    return (
        <Fragment>
            <ListItem
                button={hasChildren}
                onClick={() => hasChildren && toggleNode(root)}
                style={{ paddingLeft: level * 16 }}
            >
                {hasChildren && open && <ExpandLess />}
                {hasChildren && !open && <ExpandMore />}
                {!hasChildren && <div style={{ width: 24 }}>&nbsp;</div>}
                <ListItemText primary={root.name +" ("+ root.id+")"} />


                <ListItemSecondaryAction>
                    <EditButton record={root} basePath="/schedule" />
                </ListItemSecondaryAction>
            </ListItem>
            <Collapse in={open} timeout="auto" unmountOnExit>
                
                <List disablePadding> 
                    {childNodes.map(node => (
                        <ListItem style={{ paddingLeft: (level+1) * 32 }}>
                            <ListItemText primary={node.name +" ("+ node.id+")"} />
                            <ListItemSecondaryAction>
                                <EditButton record={node} basePath="/schedule" />
                            </ListItemSecondaryAction>
                        </ListItem>
                    ))}
                </List>
            </Collapse>
        </Fragment>
    );
};


export default TagList;
/*
                        */