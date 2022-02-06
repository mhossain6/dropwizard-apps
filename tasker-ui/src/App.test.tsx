import React from "react";
import { cleanup, render, screen } from "@testing-library/react";
import App from "./App";
 

beforeEach(() => {
  // setup a DOM element as a render target
});

afterEach(() => {
  // cleanup on exiting
  cleanup();
});

test("renders learn react link", () => {
  render(<App />);
});

