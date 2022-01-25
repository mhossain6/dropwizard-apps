import {
  Button,
  Stack,
  Box,
  TextField,
  Typography,
  Card,
  CardContent,
  Paper,
  ListItemAvatar,
  Container,
  Avatar,
  CardActions,
  Grid,
  Checkbox,
  InputAdornment,
  styled,
} from "@mui/material";
import DatePicker from "@mui/lab/DatePicker";
import React, { useEffect, useState } from "react";
import DeleteIcon from "@mui/icons-material/Delete";
import MinimizeIcon from "@mui/icons-material/Minimize";
import { Task } from "../api/task";
import EventIcon from "@mui/icons-material/Event";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import { putTask, getTasks, deleteTask, updateTask } from "../api/service";
import "./Tasker.css";
import moment from "moment";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import LocalizationProvider from "@mui/lab/LocalizationProvider";

//#require('dotenv').config();

type AppProps = {
  name: string;
}; /* use `interface` if exporting so that consumers can extend */

interface AddTaskProps {
  onSaveCallbak: Function;
}

interface TaskListProps {
  tasks: Task[];
  onChangeCallBack: Function;
}

const Item = styled(Paper)(({ theme }) => ({
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: "left",
  boxShadow: "none",
  color: theme.palette.text.secondary,
}));
const customStyle = {
  alignContent: "left",
};

// Easiest way to declare a Function Component; return type is inferred.
const Tasker = ({ name }: AppProps) => {
  const [tasks, setTasks] = React.useState<Task[]>([]);
  const [addTaskBox, setAddTaskBox] = React.useState<boolean>(false);

  React.useEffect(() => {
    getTasksFromDB();
  }, []);

  const onAddClick = (e) => {
    setAddTaskBox(true);
  };

  const onCollapseClick = (e) => {
    setAddTaskBox(false);
  };

  const onSaveClick = (task: Task) => {
    console.log("onSaveClick - ", task);

    putTask(`${process.env.REACT_APP_DB_URL}`, task)
      .then((data) => {
        console.log("response data ", data);
        addTaskToList(task);
      })
      .catch(function (error) {
        console.log("error while getting data from server" + error.toString());
      });
  };

  const getTasksFromDB = () => {
    getTasks(`${process.env.REACT_APP_DB_URL}`)
      .then((data) => {
        console.log("in getTask response ", data);
        setTasks(data);
      })
      .catch(function (error) {
        console.log("error while getting data from server" + error.toString());
      });
  };

  const addTaskToList = (task: Task) => {
    getTasksFromDB();
  };

  const onChangeCallBack = () => {
    getTasksFromDB();
  };

  return (
    <div>
      <Stack spacing={2} direction="row-reverse" alignItems="right" padding={2}>
        <Item>
          {addTaskBox ? (
            <Button
              variant="outlined"
              startIcon={<MinimizeIcon fontSize="large" />}
              onClick={onCollapseClick}
            />
          ) : (
            <Button variant="outlined" onClick={onAddClick}>
              Add
            </Button>
          )}
        </Item>
      </Stack>
      <Stack spacing={2} direction="column">
        <Item>{addTaskBox && <AddTaskView onSaveCallbak={onSaveClick} />}</Item>
      </Stack>

      <Stack spacing={2} alignItems="left">
        <Item>
          <TaskView tasks={tasks} onChangeCallBack={onChangeCallBack} />
        </Item>
      </Stack>
    </div>
  );
};

const AddTaskView: React.FC<AddTaskProps> = ({ onSaveCallbak }) => {
  const [description, setDescription] = React.useState<string>("");
  const [date, setDate] = React.useState<string>("");
  const [vaildDate, setVaildDate] = React.useState<boolean>(true);

  const onDescriptionChange = (event) => {
    setDescription(event.target.value);
  };
  const onDateChange = (event) => {
    const valid: boolean = validateDate(event.target.value);
    if (valid) {
      setDate(event.target.value);
      setVaildDate(true);
    } else {
      setVaildDate(false);
    }
  };

  const onSaveClick = (event) => {
    if (date.length == 10 && validateDate(date)) {
      const task: Task = new Task();
      task.checked = "N";
      task.date = date;
      task.description = description;

      onSaveCallbak(task);
    } else {
      setVaildDate(false);
    }
  };

  const validateDate = (dateText: string): boolean => {
    if (dateText.length == 10) {
      try {
        var date = moment(dateText);
        return date.isValid();
      } catch (error) {
        alert("invalid date!");
      }
    }
    if (dateText.length > 10) {
      alert("invalid date!");
    }
    return true;
  };

  return (
    <>
      <Card sx={{ minWidth: 275 }}>
        <CardContent style={customStyle}>
          <Stack spacing={1}>
            <Item>
              <Typography variant="h6" component="h6">
                Add Task
              </Typography>
            </Item>
            <Item>
              <Typography variant="h6" component="h6">
                Description
              </Typography>
            </Item>
            <Item>
              <TextField
                style={{ width: "100%" }}
                required
                id="description_field"
                value={description}
                onChange={onDescriptionChange}
              />
            </Item>
            <Item>
              <Typography variant="h6" component="h6" alignItems="left">
                Date
              </Typography>
            </Item>
            <Item>
              <TextField
                error={!vaildDate}
                id="date_field"
                label={vaildDate ? "" : "Error"}
                helperText={vaildDate ? "" : "Incorrect entry."}
                variant="standard"
                style={{ width: "100%" }}
                required
                value={date}
                onChange={onDateChange}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <EventIcon />
                    </InputAdornment>
                  ),
                }}
              />
            </Item>
          </Stack>
        </CardContent>
        <CardActions sx={{ display: "grid" }}>
          <Stack spacing={2} direction="row-reverse" alignContent={"right"}>
            <Item>
              <Button variant="outlined" onClick={onSaveClick}>
                Save
              </Button>
            </Item>
          </Stack>
        </CardActions>
      </Card>
    </>
  );
};

const TaskView: React.FC<TaskListProps> = ({ tasks, onChangeCallBack }) => {
  console.log("in Taskviw : tasks", tasks);

  const onCheckBoxClick = (task: Task) => {
    console.log("onCheckBoxClick - ", task);

    if (task.checked === "N") task.checked = "Y";
    else task.checked = "N";

    updateTask(`${process.env.REACT_APP_DB_URL}`, task)
      .then((data) => {
        console.log("response data ", data);
        onChangeCallBack();
      })
      .catch(function (error) {
        console.log("error while getting data from server" + error.toString());
      });
  };

  const onDeleteClick = (task: Task) => {
    console.log("onDeleteClick - ", task);

    deleteTask(`${process.env.REACT_APP_DB_URL}`, task)
      .then((data) => {
        console.log("response data ", data);
        onChangeCallBack();
      })
      .catch(function (error) {
        console.log("error while getting data from server" + error.toString());
      });
  };

  return (
    <>
      <Card sx={{ minWidth: 275 }}>
        <CardContent style={customStyle}>
          <Stack spacing={2} direction="row">
            <List style={{ width: "100%" }}>
              {tasks.map(function (task, idx) {
                return (
                  <ListItem key={idx} style={{ width: "100%" }}>
                    <Paper style={{ width: "100%" }}>
                      <Stack spacing={2} direction="row">
                        <Item sx={{ minWidth: 275 }} style={{ width: "80%" }}>
                          <Stack spacing={2}>
                            <Item>
                              <ListItemText primary={task.description} />
                            </Item>
                            <Item>
                              <Stack spacing={2} direction="row">
                                <EventIcon />
                                <ListItemText primary={task.date} />
                              </Stack>
                            </Item>
                          </Stack>
                        </Item>
                        <Item style={{ alignItems: "right" }}>
                          <Stack spacing={2} direction="row">
                            <Item>
                              <Checkbox
                                checked={task.checked === "Y" ? true : false}
                                onClick={(e) => {
                                  onCheckBoxClick(task);
                                }}
                              />
                            </Item>
                            <Item>
                              <ListItemAvatar>
                                <Avatar>
                                  <DeleteIcon
                                    onClick={(e) => {
                                      onDeleteClick(task);
                                    }}
                                  />
                                </Avatar>
                              </ListItemAvatar>
                            </Item>
                          </Stack>
                        </Item>
                      </Stack>
                    </Paper>
                  </ListItem>
                );
              })}
            </List>
          </Stack>
        </CardContent>
      </Card>
    </>
  );
};
export default Tasker;
