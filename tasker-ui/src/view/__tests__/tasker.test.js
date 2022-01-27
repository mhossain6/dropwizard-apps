import { screen, cleanup } from "@testing-library/react";
import { AddTaskView, TaskView } from "../Tasker";
import { act } from "react-dom/test-utils";
import { render, unmountComponentAtNode } from "react-dom";

let container = null;

beforeEach(() => {
  // setup a DOM element as a render target
  container = document.createElement("div");
  document.body.appendChild(container);
});

afterEach(() => {
  // cleanup on exiting
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

it("Render Add Task View and Validate Components", () => {
  const onSaveMockCallbak = jest.fn();

  act(() => {
    render(<AddTaskView onSaveMockCallbak={onSaveMockCallbak} />, container);
  });

  expect(container.querySelector("#add_task_label").textContent).toBe(
    "Add Task"
  );

  expect(container.querySelector("#task_description_lable").textContent).toBe(
    "Description"
  );

  //const description_field = document.querySelector("#description_field");
  //expect(description_field).toBe(!null);

  expect(container.querySelector("#task_date_lable").textContent).toBe("Date");

  //const date_field = document.querySelector("#date_field");
  //expect(date_field).toBe(!null);

  expect(container.querySelector("#task_save_button").textContent).toBe("Save");
});

it("Render Task List and Test CheckBox click", () => {
  const onChange = jest.fn();

  const mockTasks = [
    {
      version: 6,
      id: 1,
      checked: "N",
      description: "Sample Description",
      date: "2022-02-20",
    },
  ];
  act(() => {
    render(
      <TaskView onChangeCallBack={onChange} tasks={mockTasks} />,
      container
    );
  });

  expect(container.querySelector("#task_description1").textContent).toBe(
    "Sample Description"
  );

  expect(container.querySelector("#task_date1").textContent).toBe(
    "2022-02-20"
  );

  const checkBox = document.querySelector("#task_checked1");
  expect(checkBox.checked).toBe(false);

  //act(() => {
  //  checkBox.dispatchEvent(new MouseEvent("click", { bubbles: true }));
  //});

  //expect(onChange).toHaveBeenCalledTimes(1);
});
