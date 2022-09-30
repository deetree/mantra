#!/bin/bash

create_bin_dir() {
  if [ ! -d "$HOME/bin" ]; then
    mkdir "$HOME/bin"
  fi
}

create_mantra_dir() {
  if [ ! -d "$HOME/.mantra" ]; then
    mkdir "$HOME/.mantra"
  fi
}

download_mantra_jar() {
  wget $(wget -q -O- https://api.github.com/repos/deetree/mantra/releases/latest \
  | grep -m 1 -o 'https.*download.*mantra.*.jar') -q -O $HOME/.mantra/mantra.jar
}

save_run_script() {
  cat > "$HOME/bin/mantra" <<-"EOF"
  #!/bin/bash

  if [ ! "$(java --version &)" ]
  then
    printf "\033[0;33mYou need to have Java installed and set up properly\r\n\033[0m"
  else
    if [ $# -eq 1 ] && [ $1 = "update" ]; then
      echo "Mantra needs to be updated now, bro!"
      wget $(wget -q -O- https://api.github.com/repos/deetree/mantra/releases/latest \
        | grep -m 1 -o 'https.*download.*mantra.*.jar') -q -O $HOME/.mantra/mantra.jar
    else
      java -jar $HOME/.mantra/mantra.jar $@
    fi
  fi
EOF
}

make_mantra_executable() {
  chmod u+x "$HOME/bin/mantra"
}

add_bin_to_path() {
  if [[ ! ":$PATH:" == *":$HOME/bin:"* ]]; then
    echo "export PATH=$PATH:$HOME/bin" >> ~/.bashrc
  fi
}

check_java() {
  if [ ! "$(java --version &)" ]
  then
    printf "\033[0;33mYou need to have Java installed and set up properly\r\n\033[0m"
  fi
}

echo $0

create_bin_dir
create_mantra_dir
download_mantra_jar
save_run_script
make_mantra_executable
add_bin_to_path
check_java
