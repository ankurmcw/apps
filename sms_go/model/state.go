package model

type State struct {
    id    string
}

func NewState(id string) *State {
    return &State{
        id: id,
    }
}

func (s *State) GetId() string {
    return s.id
}
