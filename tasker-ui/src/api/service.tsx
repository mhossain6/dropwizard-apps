import axios from "axios";
import { url } from "inspector";
import { Task } from "./task";

export const getTasks = (serviceUrl: string): Promise<any[]> => {
  console.log("in getTasks api call");

  return axios
    .get<any[]>(`${serviceUrl}/task`, {
      headers: {
        "Accept": "application/json",
      },
    })
    .then((response) => {
      console.log("returned response ");
      return response.data;
    })
    .catch(function (error) {
      // handle error
      console.log(error);
      return [];
    });
};

export const putTask = (serviceUrl: string, task: Task): Promise<any[]> => {
  console.log("in getTasks api call", task);

  return axios
    .put<any[]>(`${serviceUrl}/task`, task, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => {
      console.log("returned response ");
      return response.data;
    })
    .catch(function (error) {
      // handle error
      console.log(error);
      return [];
    });
};

export const updateTask = (serviceUrl: string, task: Task): Promise<any[]> => {
  console.log("in updateTask api call", task);

  return axios
    .post<any[]>(`${serviceUrl}/task`, task, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => {
      console.log("returned response ");
      return response.data;
    })
    .catch(function (error) {
      // handle error
      console.log(error);
      return [];
    });
};

export const deleteTask = (serviceUrl: string, task: Task): Promise<any[]> => {
  console.log("in updateTask api call", task);

  return axios
    .delete<any[]>(`${serviceUrl}/task/` + task.id, {
      headers: {
      },
    })
    .then((response) => {
      console.log("returned response ", response.data);
      return response.data;
    })
    .catch(function (error) {
      // handle error
      console.log(error);
      return [];
    });
};
