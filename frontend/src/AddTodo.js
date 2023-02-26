import React, {useState} from "react";
import {Button, Grid, TextField} from "@mui/material";

const AddTodo = (props) => {
    const [item, setItem] = useState({title: ""});
    const addItem = props.addItem;

    const onInputChange = (e) => {
        setItem({title: e.target.value});
        console.log(item);
    }

    const onButtonClick = () => {
        addItem(item);
        setItem({title: ""});
    }

    const enterKeyEventHandler = (e) => {
        if (e.key === 'Enter') {
            onButtonClick();
        }
    }

    return(
        <Grid container style={{marginTop: 20}}>
            <Grid xs={11} md={11} item style={{paddingRight: 16}}>
                <TextField placeholder={"여기에 TodoList에 추가할 항목을 넣으세요"} fullWidth
                onChange={onInputChange} value={item.title} onKeyPress={enterKeyEventHandler}/>
            </Grid>
            <Grid xs={1} md={1} item>
                <Button fullWidth style={{height: '100%'}} color="secondary" variant="outlined"
                 onClick={onButtonClick}>
                    +
                </Button>
            </Grid>
        </Grid>
    )
}

export default AddTodo;