
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";
import Header from '../Header';


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

  test("Header has name Tasker", () => {
    act(() => {
      render(<Header />, container);
    });
    expect(container.textContent).toBe("Tasker");
  });

 