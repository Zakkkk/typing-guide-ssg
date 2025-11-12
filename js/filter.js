window.onload = function () {
  const navElements = document.querySelectorAll("nav a");
  const filterSearch = document.querySelector("#filter-headings");

  if (navElements != undefined && filterSearch != undefined) {
    filterSearch.addEventListener("keyup", (e) => {
      let filterFor = filterSearch.value.trim().toLowerCase().split(" ");

      navElements.forEach((navElement) => {
        if (
          filterFor == "" ||
          navElement.innerText
            .toLowerCase()
            .split(" ")
            .some((title) => title.includes(filterFor))
        ) {
          navElement.classList.remove("hidden");
        } else {
          navElement.classList.add("hidden");
        }
      });
    });
  } else console.log("Nav elements or filter search was not found.");
};
