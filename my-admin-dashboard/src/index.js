import * as React from "react";
import * as ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider, } from "react-router-dom";
import ErrorPage from "./components/Common/Error-page";
import Index from "./routes/index";
import StatsPage from "./routes/stats";
import Login, { action as loginAction, loader as loginLoader } from "./routes/login";
import Main, { loader as mainLoader } from "./routes/main";
import Dashboard, { loader as dashboardLoader } from "./routes/dashboard";
import DashboardContent, { loader as dashboardContentLoader } from "./routes/dashboard-content";
import DatabaseDetail, { loader as dashboardDetailLoader } from "./routes/detail";
import Messages, { action as messageAction, loader as messageLoader } from "./routes/messages";
import Database from "./routes/database";
import Setting from "./routes/setting";
import Profile from "./routes/profile";


const router = createBrowserRouter([
  {
    path: "/",
    element: <Main />,
    loader: mainLoader,
    errorElement: <ErrorPage />,

  },
  {
    path: "/login",
    element: <Login />,
    errorElement: <Login />,
    loader: loginLoader,
    action: loginAction,
  },
  {
    path: "/main",
    element: <Main />,
    loader: mainLoader,
    errorElement: <ErrorPage />,
    children: [
      {
        errorElement: <ErrorPage />,
        children: [
          {
            path: "setting",
            element: <Setting />,
            loader: mainLoader,
            errorElement: <ErrorPage />,
          },
          {
            path: "profile",
            element: <Profile />,
            loader: mainLoader,
            errorElement: <ErrorPage />,
          },
          {
            path: "dashboard",
            element: <Dashboard />,
            loader: dashboardLoader,
            children: [
              {
                path: "",
                element: <Index />
              },
              {
                path: "database",
                element: <DashboardContent />,
                loader: dashboardContentLoader,
                children: [
                  {
                    path: ":table",
                    element: <Database />,
                    loader: dashboardContentLoader,
                    children: [
                    ]
                  },
                  {
                    path: ":table/:detail",
                    element: <DatabaseDetail />,
                    loader: dashboardDetailLoader,
                  },
                  {
                    path: ":table/add",
                    element: <Database />,
                    loader: dashboardContentLoader,
                    children: [
                    ]
                  },
                ]
              },
              {
                path: "message",
                element: <Messages />,
                action: messageAction,
                loader: messageLoader,
              }, {
                path: "stats",
                element: <StatsPage />,
                loader: dashboardContentLoader,
              }
            ]
          },
        ]
      }
    ]
  }

]);

ReactDOM.createRoot(document.getElementById("root")).render(
  // <React.StrictMode>
  <RouterProvider router={router} />
  // </React.StrictMode>
);