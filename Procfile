if [ ! -f $BUILD_DIR/Procfile ]; then
cat <<EOF
default_process_types:
    web: gradle run server heroku-config.yml
EOF
fi