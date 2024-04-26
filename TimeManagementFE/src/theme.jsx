import { createTheme } from '@mui/material/styles';

const theme = createTheme({
    // Colors
    palette: {
        primary: {
            main: '#275070',
        },
        secondary: {
            main: '#955670',
        },
    },
    // Buttons
    shape: {
        borderRadius: 12,
    },
    // Card content
    components: {
        MuiCardContent: {
            styleOverrides: {
                root: {
                    backgroundColor: '#EFF1F4',
                    color: '#275070',
                    '&:hover': {
                        backgroundColor: '#EFF1F4',
                    },
                },
            },
        },
    },
});

export default theme;